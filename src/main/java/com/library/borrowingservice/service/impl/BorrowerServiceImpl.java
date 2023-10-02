package com.library.borrowingservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.borrowingservice.entity.Borrower;
import com.library.borrowingservice.repository.BorrowerRepository;
import com.library.borrowingservice.service.BorrowerService;

@Service
public class BorrowerServiceImpl implements BorrowerService {

	@Autowired
	private BorrowerRepository borrowerRepository;

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
		borrowerRepository.deleteById(id);
	}

}
