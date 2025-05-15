package com.dex.team.repository;

import com.dex.team.entity.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TimeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TimeRepository timeRepository;

    @Test
    public void whenFindById_thenReturnTime() {
        Time time = new Time();
        time.setData(LocalDate.of(2023, 1, 1));
        entityManager.persistAndFlush(time);

        Optional<Time> found = timeRepository.findById(time.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getData()).isEqualTo(time.getData());
    }

    @Test
    public void whenFindByData_thenReturnTimes() {
        LocalDate data = LocalDate.of(2023, 1, 15);

        Time time1 = new Time();
        time1.setData(data);
        entityManager.persist(time1);

        Time time2 = new Time();
        time2.setData(data);
        entityManager.persist(time2);

        entityManager.flush();

        List<Time> found = timeRepository.findByData(data);

        assertThat(found).isNotNull();
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getData()).isEqualTo(data);
        assertThat(found.get(1).getData()).isEqualTo(data);
    }

    @Test
    public void whenFindByPeriodo_thenReturnTimesNoPeriodo() {
        LocalDate data1 = LocalDate.of(2023, 1, 1);
        LocalDate data2 = LocalDate.of(2023, 1, 15);
        LocalDate data3 = LocalDate.of(2023, 2, 1);

        Time time1 = new Time();
        time1.setData(data1);

        Time time2 = new Time();
        time2.setData(data2);

        Time time3 = new Time();
        time3.setData(data3);

        entityManager.persist(time1);
        entityManager.persist(time2);
        entityManager.persist(time3);
        entityManager.flush();

        LocalDate inicioPeriodo = LocalDate.of(2023, 1, 1);
        LocalDate fimPeriodo = LocalDate.of(2023, 1, 31);

        List<Time> timesNoPeriodo = timeRepository.findByPeriodo(inicioPeriodo, fimPeriodo);

        assertThat(timesNoPeriodo).hasSize(2)
                .extracting(Time::getData)
                .containsExactlyInAnyOrder(data1, data2);
    }

    @Test
    public void whenFindByPeriodoWithNullDates_thenReturnAllTimes() {
        Time time1 = new Time();
        time1.setData(LocalDate.of(2023, 1, 1));

        Time time2 = new Time();
        time2.setData(LocalDate.of(2023, 2, 1));

        entityManager.persist(time1);
        entityManager.persist(time2);
        entityManager.flush();

        List<Time> allTimes = timeRepository.findByPeriodo(null, null);

        assertThat(allTimes).hasSize(2);
    }

    @Test
    public void whenSaveTime_thenReturnSavedTime() {
        Time time = new Time();
        time.setData(LocalDate.now());

        Time saved = timeRepository.save(time);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getData()).isEqualTo(time.getData());
    }
}
