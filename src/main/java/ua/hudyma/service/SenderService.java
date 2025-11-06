package ua.hudyma.service;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.company.Company;
import com.devskiller.jfairy.producer.person.Address;
import com.devskiller.jfairy.producer.person.Person;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.Profile;
import ua.hudyma.domain.Sender;
import ua.hudyma.enums.EntityType;
import ua.hudyma.repository.SenderRepository;
import ua.hudyma.util.IdGenerator;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static ua.hudyma.util.IdGenerator.getRandomEnum;

@Service
@RequiredArgsConstructor
@Log4j2
public class SenderService {
    private final SenderRepository senderRepository;
    private final Fairy fairy = Fairy.create();

    @Transactional
    public void createSender(int quantity) {
        Supplier<Sender> senderSupplier = switch (getRandomEnum(EntityType.class)) {
            case PERSON -> this::createPersonSender;
            case COMPANY -> this::createCompanySender;
        };
        List<Sender> senders = Stream
                .generate(senderSupplier)
                .limit(quantity)
                .toList();
        senderRepository.saveAll(senders);
        log.info("::: SUCC created {} SENDERS", quantity);
    }

    public Sender getById(Long senderId) {
        return senderRepository.findById(senderId).orElseThrow(
                () -> new EntityNotFoundException
                        ("Sender " + senderId + " NOT FOUND"));
    }

    private Sender createPersonSender() {
        Person person = fairy.person();
        return buildSender(EntityType.PERSON, createPersonProfile(person));
    }

    private Sender createCompanySender() {
        Company company = fairy.company();
        return buildSender(EntityType.COMPANY, createCompanyProfile(company));
    }

    private Sender buildSender(EntityType type, Profile profile) {
        Sender sender = new Sender();
        sender.setEntityType(type);
        sender.setProfile(profile);
        sender.setSenderCode(IdGenerator.generateSenderCode(type));
        return sender;
    }

    private Profile createPersonProfile(Person person) {
        return Profile.builder()
                .birthday(person.getDateOfBirth())
                .email(person.getEmail())
                .phoneNumber(person.getTelephoneNumber())
                .address(formatAddress(person.getAddress()))
                .name(person.getFullName())
                .build();
    }

    private Profile createCompanyProfile(Company company) {
        return Profile.builder()
                .birthday(IdGenerator.generateIssuedOn())
                .email(company.getEmail())
                .name(company.getName())
                .phoneNumber(IdGenerator.generatePhoneNumber())
                .address("company address")
                .build();
    }

    private static String formatAddress(Address address) {
        return String.format("%s, %s, %s/%s",
                address.getCity(),
                address.getStreet(),
                address.getAddressLine1(),
                address.getApartmentNumber());
    }
}
