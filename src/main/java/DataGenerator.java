import com.github.javafaker.Faker;
import model.User;


public class DataGenerator {
    public static User randomUser() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String name = faker.name().firstName();
        return new User(email, password, name);
    }
}
