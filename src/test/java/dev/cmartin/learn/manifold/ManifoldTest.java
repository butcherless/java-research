package dev.cmartin.learn.manifold;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ManifoldTest {

    @Test
    void shouldTestTuple() {
        // given
        var developerName = "Softman";
        var developerAge = 25;
        var tuple = (name:developerName, age:developerAge);

        // when
        var text = String.format("Manifold tuple: %s - %d", tuple.name, tuple.age);

        // then
        assertThat(text)
                .isEqualTo("Manifold tuple: " + developerName + " - " + developerAge);
    }

    @Test
    void shouldTestExtensions() {
        // given
        String text = "I am a soft String";

        // when
        var result = text.beStrong();

        // then
        assertThat(result)
                .isEqualTo("I will never be a soft string again: " + text);
    }

    @Test
    void shouldTestExtensionsToJsonArray() {
        // given
        String name = "Softman";

        // when
        var result = name.toJsonArray();

        // then
        assertThat(result).isEqualTo("""
                ["S","o","f","t","m","a","n"]
                """.strip());
    }

    @Test
    void shouldTestDefaultPropertyValues() {
        // given
        var country = new Country("Spain", "ES", ());

        // when & then
        //assertThat(country.name).isEqualTo("Spain");
        //assertThat(country.code).isEqualTo("ES");
        //assertThat(country.population).isEqualTo(0);
    }

    @Test
    void shouldTestNamedPropertyValues() {
        // given
        var country = new Country("Spain", "ES", (population:47_000_000, extension:505_992.0));

        // when & then
        assertThat(country.name).isEqualTo("Spain");
        assertThat(country.code).isEqualTo("ES");
        //assertThat(country.population).isEqualTo(47_000_000);
        //assertThat(country.extension).isEqualTo(505_992.0);
    }

    @Test
    void shouldTestCollectionMap() {
        //given
        var numbers = List.of(1, 2, 3, 4, 5);
        // when
        var results = numbers.map(n -> n * 2).toList();
        // then
        assertThat(results)
                .containsExactly(2, 4, 6, 8, 10);
    }

    @Test
    void shouldTestEmptyCollection() {
        //given
        var emptyList = List.of();
        // when
        var result = emptyList.isNullOrEmpty();
        // then
        assertThat(result)
                .isTrue();
    }

    @Test
    void shouldTestNullCollection() {
        //given
        List<Integer> nullList = null;
        // when
        var result = nullList.isNullOrEmpty();
        // then
        assertThat(result).
                isTrue();
    }

    @Test
    void shouldTestNotEmptyCollection() {
        //given
        var numbers = List.of(1, 2, 3);
        // when
        var result = numbers.isNotEmpty();
        // then
        assertThat(result)
                .isTrue();
    }

    @Test
    void shouldTestStringInterpolation() {
        // given
        var name = "Softman";
        var age = 25;
        // when
        var result = "Hello, my name is ${name} and I am ${age} years old";
        // then
        assertThat(result)
                .isEqualTo("Hello, my name is Softman and I am 25 years old");
    }
}