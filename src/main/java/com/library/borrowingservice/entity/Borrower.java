package com.library.borrowingservice.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Class representing a borrower in the library application.")
@Entity
@Data
@NoArgsConstructor
@Table(name = "borrower")
@JsonPropertyOrder({ "id", "firstName", "lastName", "email", "address", "membershipNo", "bookLimit" })
public class Borrower {

	@ApiModelProperty(notes = "Unique identifier of the Borrower.", example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(notes = "Fisrt Name of the Borrower.", example = "Ankit", required = true)
	@NotBlank
	private String firstName;

	@ApiModelProperty(notes = "Last Name of the Borrower.", example = "Tiwari", required = false)
	private String lastName;

	@ApiModelProperty(notes = "Email address of the Borrower.", example = "ankit.tiwari@gmail.com", required = true)
	@Email(message = "Email Address")
	private String email;

	@ApiModelProperty(notes = "Address of the Borrower.", example = "No:2, 1st Cross Street, Chennai", required = false)
	private String address;

	@ApiModelProperty(notes = "Membership number of the Borrower.", example = "CHN2043", required = true)
	private String membershipNo;

	@ApiModelProperty(notes = "Number of books allowed to the Borrower.", example = "10", required = true)
	private Integer bookLimit;

	public Borrower(Long id, String firstName, String lastName, String email, String address, String membershipNo,
			Integer bookLimit) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.membershipNo = membershipNo;
		this.bookLimit = bookLimit;
	}

	public Borrower updateWith(Borrower borrower) {
		return new Borrower(this.id, borrower.firstName, borrower.lastName, borrower.email, borrower.address,
				borrower.membershipNo, borrower.bookLimit);
	}

}
