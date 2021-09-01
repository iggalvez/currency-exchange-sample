package com.in28minutes.microservices.currencyexchangeservice;

import com.in28minutes.microservices.currencyexchangeservice.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.pow;

@SpringBootApplication
public class CurrencyExchangeServiceApplication implements CommandLineRunner {
	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Country brazil = createBrazil();
		Country  argentina =createArgentina();

		Country paraguay = createParaguay();
		Country uruguay = createUruguay();
		Set<Country> countries = generateParameterList(brazil, argentina, paraguay, uruguay);
		RegionalInternationalUnit unit = createRegionalUnit(countries);
		entityManager.persist(unit);
		entityManager.close();
	}


	private Set<Country> generateParameterList(Country brazil, Country argentina, Country paraguay, Country uruguay) {
		Set<Country> countries = new HashSet<>();
		countries.add(argentina);
		countries.add(brazil);
		countries.add(paraguay);
		countries.add(uruguay);
		return countries;
	}

	private RegionalInternationalUnit createRegionalUnit(Set<Country> countries) {
		Continent continent = crateContinent(countries);
		RegionalInternationalUnit unit = new RegionalInternationalUnit();
		doGenerateInternationalUnit(countries, continent, unit);
		return unit;
	}


	private Continent crateContinent(Set<Country> countries) {
		Continent continent = new Continent();
		continent.setName("Sudamerica");
		continent.setCountries(countries);
		continent.setSurfaceInSquaredKm(BigDecimal.valueOf(3*pow(10,5)));
		entityManager.persist(continent);
		return continent;
	}

	private void doGenerateInternationalUnit(Set<Country> countries, Continent continent, RegionalInternationalUnit unit) {
		Set<Continent> continents = new HashSet<>();
		continents.add(continent);
		unit.setContinents(continents);
		unit.setCountries(countries);
		unit.setName("Mercosur");
		unit.setSigningDate(LocalDate.of(1994,03,10));
		unit.setUnitFormalStart(LocalDate.of(1994,5,10));
		unit.setExpirationDate(LocalDate.of(3022,5,10));

		//entityManager.persist(unit);
	}

	private Country createUruguay() {
		Language language = new Language("Castellano", "CS", new HashSet<>());
		Currency currency = new Currency("URY","Peso uruguayo",null);
		Country uruguay = createCountry(language,currency,"Uruguay",3*pow(10,6),
				BigDecimal.valueOf(5000));
		return uruguay;
	}

	private Country createBrazil() {
		Language language = new Language("Portuguese", "PT", new HashSet<>());
		Currency currency = new Currency("REAL","Real",null);
		Country brazil = createCountry(language,currency,"Brazil",pow(10,8),
				BigDecimal.valueOf(3*pow(10,4)));
		return brazil;
	}

	private Country createParaguay() {
		Language language = new Language("Guarani", "GR", new HashSet<>());
		Currency currency = new Currency("GR","Guarani",null);
		Country paraguay = createCountry(language,currency,"Brazil",pow(10,8),
				BigDecimal.valueOf(8000));
		return paraguay;
	}

	private Country createCountry(Language language, Currency currency, String countryName,
								  double population,
								  BigDecimal surface) {
		Set<Language> languages = new HashSet<>();
		Set<Currency> currencies = new HashSet<>();
		Country country = create(language, currency, countryName, population, surface, languages, currencies);
		return country;
	}


	private Country create(Language language, Currency currency, String countryName, double population, BigDecimal surface, Set<Language> languages, Set<Currency> currencies) {
		languages.add(language);
		logger.info("language = " + language);
		Country country = generateCountry(currency, languages,
				currencies, countryName, population,
				surface);
		setLogAndPersist(language, currency, languages, country);
		return country;
	}

	private void setLogAndPersist(Language language, Currency currency, Set<Language> languages, Country country) {
		setIssuerCountry(language, currency, country);
		doSetLanguages(languages, country);
		doLog(language, currency, country);
		persistCountry(language, currency, country);
	}

	private void doSetLanguages(Set<Language> languages, Country country) {
		country.setLanguages(languages);
		country.setOfficialLanguages(languages);
	}

	private void doLog(Language language, Currency currency, Country country) {
		logger.info("language = " + language);
		logger.info("country = " + country);
		logger.info("currency = " + currency);
	}

	private void setIssuerCountry(Language language, Currency currency, Country country) {
		currency.setCurrencyIssuer(country);
		language.getCountries().add(country);
	}


	private Country generateCountry(Currency currency, Set<Language> languages, Set<Currency> currencies, String countryName,
									double pow, BigDecimal bigDecimal) {

		Country country = new Country(countryName, BigDecimal.valueOf(pow),
				languages, null, currencies, languages,
				bigDecimal, currency);
		currency.setCurrencyIssuer(country);
		country.setOfficialIssuedCurrency(currency);

		return country;
	}

	private Country createArgentina(){
		Language language = new Language("Castellano", "CS", new HashSet<>());
		Currency currency = new Currency("ARS","Pesos Argentinos",null);
		Country argentina = createCountry(language,currency,"Argentina",48*pow(10,6),
				BigDecimal.valueOf(2*pow(10,4)));
		return argentina;
	}

	private void persistCountry(Language language, Currency currency, Country country) {
		entityManager.persist(language);
		entityManager.persist(currency);
		entityManager.persist(country);
	}
}
