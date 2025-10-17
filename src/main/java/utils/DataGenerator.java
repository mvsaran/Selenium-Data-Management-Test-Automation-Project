package utils;

import com.github.javafaker.Faker;
import pojo.UserData;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static UserData generateUserData() {
        return new UserData(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().streetAddress(),
                faker.address().city(),
                faker.address().state(),
                faker.address().zipCode(),
                faker.phoneNumber().cellPhone(),
                faker.idNumber().ssnValid(),
                faker.name().username(),
                faker.internet().password(8, 12)
        );
    }
}
