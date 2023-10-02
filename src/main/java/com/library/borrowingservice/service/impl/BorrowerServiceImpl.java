package com.library.borrowingservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.library.borrowingservice.entity.Borrower;
import com.library.borrowingservice.repository.BorrowerRepository;
import com.library.borrowingservice.service.BorrowerService;
import com.library.common.exception.LibraryException;

@Service
public class BorrowerServiceImpl implements BorrowerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BorrowerService.class);

	@Autowired
	private BorrowerRepository borrowerRepository;

	private final RestTemplate restTemplate;
	private static final String BOOK_SERVICE_URL = "http://localhost:8083/api/books/borrower";

	public BorrowerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<Borrower> getAllBorrowers() {
		return borrowerRepository.findAll();
	}

	@Override
	public Borrower getBorrowerById(long id) {
		Optional<Borrower> borrower = borrowerRepository.findById(id);
		return (borrower.isPresent() ? borrower.get() : null);
	}

	@Override
	public Borrower createBorrower(Borrower borrower) {
		return borrowerRepository.save(borrower);
	}

	@Override
	public Borrower updateBorrower(long id, Borrower borrower) {
		Optional<Borrower> updatedBorrower = borrowerRepository.findById(id).map(existingBorrower -> {
			return borrowerRepository.save(existingBorrower.updateWith(borrower));
		});

		return (updatedBorrower.isPresent() ? updatedBorrower.get() : null);
	}

	@Override
	public void deleteBorrower(long id) {
		// Update books before deleting borrower

		ResponseEntity<String> result = restTemplate.exchange(BOOK_SERVICE_URL + "/" + id, HttpMethod.PUT, null,
				String.class);
		LOGGER.info("Response Status: " + result.getStatusCode());
		LOGGER.info("Response Body: " + result.getBody());

		if (result.getStatusCode() == HttpStatus.OK || result.getStatusCode() == HttpStatus.NO_CONTENT) {
			borrowerRepository.deleteById(id);
		} else {
			throw new LibraryException("delete-failed", result.getStatusCode() + " - Received from book service.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
