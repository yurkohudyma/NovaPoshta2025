package ua.hudyma.service;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.company.Company;
import com.devskiller.jfairy.producer.person.Address;
import com.devskiller.jfairy.producer.person.Person;
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
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class SenderService {
    private final SenderRepository senderRepository;

    @Transactional
    public void createSender(Integer quantity, EntityType type) {
        List<Sender> list;
        if (type == EntityType.PERSON) {
            list = Stream
                    .generate(this::createPerson)
                    .limit(quantity)
                    .toList();
        } else {
            list = Stream
                    .generate(this::createCompany)
                    .limit(quantity)
                    .toList();
        }
        senderRepository.saveAll(list);
        log.info("::: SUCC created " + quantity + " persons");
    }

    private Sender createCompany() {
        var company = Fairy.create().company();
        var profile = createCompanyProfile(company);
        var sender = new Sender();
        sender.setEntityType(EntityType.COMPANY);
        sender.setProfile(profile);
        return sender;
    }

    private Sender createPerson() {
        var person = Fairy.create().person();
        var profile = createPersonProfile(person);
        var sender = new Sender();
        sender.setProfile(profile);
        sender.setEntityType(EntityType.PERSON);
        return sender;
    }

    private Profile createCompanyProfile(Company company) {
        return Profile
                .builder()
                .birthday(IdGenerator.generateIssuedOn())
                .email(company.getEmail())
                .name(company.getName())
                .phoneNumber(IdGenerator.generatePhoneNumber())
                .address(compileAddress(company))
                .build();
    }

    private static Profile createPersonProfile(Person person) {
        return Profile
                .builder()
                .birthday(person.getDateOfBirth())
                .email(person.getEmail())
                .phoneNumber(person.getTelephoneNumber())
                .address(compileAddress(person))
                .name(person.getFullName())
                .build();
    }

    private static String compileAddress(Object object) {
        if (object instanceof Person person) {
            Address address = person.getAddress();
            return formatAddress(address);
        } else if (object instanceof Company company) {
            return "company address";
        } else {
            throw new IllegalArgumentException("Unsupported type: " + object.getClass());
        }
    }

    private static String formatAddress(Address address) {
        return address.getCity() + ", " +
                address.getStreet() + ", " +
                address.getAddressLine1() + "/" +
                address.getApartmentNumber();
    }
}
