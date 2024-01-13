package com.example.bllessons.repository;

import com.example.bllessons.domain.Lesson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2
)
class LessonRepositoryTest {
    @Autowired
    private LessonRepository lessonRepository;

    @Test
    public void save() {
        Lesson lesson = new Lesson();
        lesson.setTheme("theme");
        lesson.setCourseId(UUID.randomUUID());
        lesson.setText("text");

        Lesson save = lessonRepository.save(lesson);

        Assertions.assertThat(save.getId()).isNotNull();
        Assertions.assertThat(save.getTheme()).isEqualTo("theme");
    }

    @Test
    public void getById() {
        Lesson lesson = new Lesson();
        lesson.setTheme("theme");
        Lesson saved = lessonRepository.save(lesson);

        Optional<Lesson> byId = lessonRepository.findById(saved.getId());

        Assertions.assertThat(byId).isNotEmpty();
        Assertions.assertThat(byId.get().getTheme()).isEqualTo("theme");
    }
}