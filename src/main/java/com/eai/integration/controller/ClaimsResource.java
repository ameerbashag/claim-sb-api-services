/**
 * 
 */
package com.eai.integration.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eai.integration.bo.Claims;
import com.eai.integration.bo.ClaimsResponse;
import com.eai.integration.bo.repository.ClaimsRepository;

/**
 * @author ambasha
 *
 */

@RestController
public class ClaimsResource {

	@Autowired
	private ClaimsRepository claimsRepository;

	@GetMapping("/claims")
	public List<Claims> retrieveAllClaims() {
		return claimsRepository.findAll();
	}

	@GetMapping("/claims/{id}")
	public Claims retrieveClaim(@PathVariable Integer id) {
		Optional<Claims> claim = claimsRepository.findById(id);

		if (!claim.isPresent())
			System.out.println("ID Not found");
		// throw new ClaimNotFoundException("id-" + id);

		return claim.get();
	}

	@DeleteMapping("/claims/{id}")
	public void deleteClaim(@PathVariable Integer id) {
		claimsRepository.deleteById(id);
	}

	@PostMapping("/claims")
	public ClaimsResponse createClaim(@RequestBody Claims claims) {
		 Random r = new Random( System.currentTimeMillis() );
		Integer claimnumber = 10000 + r.nextInt(20000);
		claims.setClaimNumber(claimnumber);
		Claims saveClaim = claimsRepository.save(claims);
		ClaimsResponse claimsResponse = new ClaimsResponse();
		claimsResponse.setStatus("SUCCESS");
		claimsResponse.setMessage("Claim Created Successfully");
		claimsResponse.setClaimNumber(claimnumber.toString());
		return claimsResponse;
	}

	@PutMapping("/claims/{id}")
	public ResponseEntity<Object> updateClaim(@RequestBody Claims claim, @PathVariable Integer id) {

		Optional<Claims> claims = claimsRepository.findById(id);

		if (!claims.isPresent())
			return ResponseEntity.notFound().build();

		claim.setId(id);

		claimsRepository.save(claim);

		return ResponseEntity.noContent().build();
	}

}
