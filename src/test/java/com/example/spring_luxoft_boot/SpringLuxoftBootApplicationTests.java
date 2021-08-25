package com.example.spring_luxoft_boot;

import com.example.spring_luxoft_boot.model.Country;
import com.example.spring_luxoft_boot.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringLuxoftBootApplicationTests {

	private static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
			{"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
			{"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
			{"Russian Federation", "RU"}, {"Sweden", "SE"},
			{"Switzerland", "CH"}, {"United Kingdom", "GB"},
			{"United States", "US"}};

	@Autowired
	private CountryRepository countryRepository;

	private List<Country> expectedCountryList = new ArrayList<>();
	private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
	private Country countryWithChangedName = new Country(1, "Russia", "RU");

	@BeforeEach
	public void setUp() {
		initExpectedCountryLists();
	}


	@Test
	@DirtiesContext
	public void testCountryList() {
		List<Country> countryList = countryRepository.getCountryList();
		Assertions.assertNotNull(countryList);
		Assertions.assertEquals(expectedCountryList.size(), countryList.size());
		for (int i = 0; i < expectedCountryList.size(); i++) {
			Assertions.assertEquals(expectedCountryList.get(i), countryList.get(i));
		}
	}

	@Test
	@DirtiesContext
	public void testCountryListStartsWithA() {
		List<Country> countryList = countryRepository.getCountryListStartWith("A");
		Assertions.assertNotNull(countryList);
		Assertions.assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
		for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
			Assertions.assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
		}
	}

	@Test
	@DirtiesContext
	public void testCountryChange() {
		countryRepository.updateCountryName("RU", "Russia");
		Assertions.assertEquals(countryWithChangedName, countryRepository.getCountryByCodeName("RU"));
	}

	private void initExpectedCountryLists() {
		for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
			String[] countryInitData = COUNTRY_INIT_DATA[i];
			Country country = new Country(i, countryInitData[0], countryInitData[1]);
			expectedCountryList.add(country);
			if (country.getName().startsWith("A")) {
				expectedCountryListStartsWithA.add(country);
			}
		}
	}
}
