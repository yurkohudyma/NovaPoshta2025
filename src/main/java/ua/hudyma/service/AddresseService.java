package ua.hudyma.service;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.company.Company;
import com.devskiller.jfairy.producer.person.Address;
import com.devskiller.jfairy.producer.person.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.Addressee;
import ua.hudyma.domain.Profile;
import ua.hudyma.enums.EntityType;
import ua.hudyma.repository.AddresseRepository;
import ua.hudyma.util.IdGenerator;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddresseService {

    private final AddresseRepository addresseRepository;
    private final Fairy fairy = Fairy.create();

    @Transactional
    public void createAddressee(int quantity, EntityType type) {
        Supplier<Addressee> senderSupplier = switch (type) {
            case PERSON -> this::createPersonAddressee;
            case COMPANY -> this::createCompanyAddressee;
        };
        List<Addressee> senders = Stream
                .generate(senderSupplier)
                .limit(quantity)
                .toList();
        addresseRepository.saveAll(senders);
        log.info("::: SUCC created {} of type {}", quantity, type);
    }

    private Addressee createPersonAddressee () {
        var person = fairy.person();
        return buildSender(EntityType.PERSON, createPersonProfile(person));
    }

    private Addressee createCompanyAddressee () {
        Company company = fairy.company();
        return buildSender(EntityType.COMPANY, createCompanyProfile(company));
    }

    private Addressee buildSender(EntityType type, Profile profile) {
        var addressee = new Addressee();
        addressee.setEntityType(type);
        addressee.setProfile(profile);
        addressee.setAddresseeCode(IdGenerator.generateSenderCode(type));
        return addressee;
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
