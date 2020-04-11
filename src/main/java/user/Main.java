package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

import static user.User.Gender.MALE;

public class Main {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();
            User user = User.builder()
                    .name("James Bond")
                    .username("007")
                    .password("password")
                    .email("example@example.com")
                    .gender(MALE)
                    .enabled(true)
                    .dob(LocalDate.parse("1920-11-11"))
                    .build();
            dao.insert(user);
            dao.findById(user.getId());
            dao.findByUsername(user.getUsername());
            dao.list();
            dao.delete(user);
        }

    }

}
