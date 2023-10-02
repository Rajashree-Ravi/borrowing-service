package com.library.borrowingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.common.exception.LibraryException;

import io.swagger.annotations.*;

import com.library.borrowingservice.entity.Borrower;
import com.library.borrowingservice.service.BorrowerService;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage borrowers in the library application")
@RequestMapping("/api/borrowers")
public class BorrowerController {

	@Autowired
	private BorrowerService borrowerService;

	@GetMapping
	@ApiOperation(value = "View all borrowers", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all borrowers"),
			@ApiResponse(code = 204, message = "Borrowers list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<Borrower>> getAllBorrowers() {
		List<Borrower> borrowers = borrowerService.getAllBorrowers();

		if (borrowers.isEmpty())
			throw new LibraryException("no-content", "Borrowers list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(borrowers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific borrower with the specified borrower id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved borrower with the borrower id"),
			@ApiResponse(code = 404, message = "Borrower with specified borrower id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<Borrower> getBorrowerById(@PathVariable("id") long id) {
		Borrower borrower = borrowerService.getBorrowerById(id);

		if (borrower != null)
			return new ResponseEntity<>(borrower, HttpStatus.OK);
		else
			throw new LibraryException("borrower-not-found", String.format("Borrower with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new borrower", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a borrower"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<Borrower> createBorrower(@RequestBody Borrower borrower) {
		try {
			return new ResponseEntity<>(borrowerService.createBorrower(borrower), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a borrower information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated borrower information"),
			@ApiResponse(code = 404, message = "Borrower with specified borrower id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<Borrower> updateBorrower(@PathVariable("id") long id, @RequestBody Borrower borrower) {
		Borrower updatedBorrower = borrowerService.updateBorrower(id, borrower);

		if (updatedBorrower != null)
			return new ResponseEntity<>(updatedBorrower, HttpStatus.OK);
		else
			throw new LibraryException("borrower-not-found", String.format("Borrower with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a borrower", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted borrower information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteBorrower(@PathVariable("id") long id) {
		try {
			borrowerService.deleteBorrower(id);
			return new ResponseEntity<>("Borrower deleted successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
