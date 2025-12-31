package com.uos.all4runner.service.review;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.domain.dto.request.ReviewRequest;
import com.uos.all4runner.domain.dto.response.ReviewResponse;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.category.CategoryRepository;
import com.uos.all4runner.repository.review.ReviewRepository;
import com.uos.all4runner.repository.route.RouteRepository;
import com.uos.all4runner.util.AccountCreation;
import com.uos.all4runner.util.AuthenticationCreation;
import com.uos.all4runner.util.CategoryCreation;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReviewServiceTest {

	@Autowired
	ReviewService reviewService;

	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	RouteRepository routeRepository;
	@Autowired
	CategoryRepository categoryRepository;

	Account testAccount;
	Route testRoute;

	@BeforeEach
	void setUp() throws Exception {
		testAccount = AccountCreation.createMember();
		accountRepository.save(testAccount);

		Category testCategory = CategoryCreation.createCategory();
		categoryRepository.save(testCategory);

		testRoute = Route.createTemporaryRoute(
			testAccount,
			testCategory
		);
		testRoute.setPublicStatus();
		routeRepository.save(testRoute);
	}

	@Test
	void 리뷰생성_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			testAccount.getId(),
			AccountRole.MEMBER
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		ReviewRequest.Create request = new ReviewRequest.Create("내용");

		// when
		reviewService.createReview(
			testAccount.getId(),
			testRoute.getId(),
			request
		);

		// then
		Assertions.assertEquals("내용",testAccount.getReviews().get(0).getContent());
		Assertions.assertEquals("내용",testRoute.getReviews().get(0).getContent());
	}

	@Test
	void 리뷰변경_성공() {
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			testAccount.getId(),
			AccountRole.MEMBER
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		ReviewRequest.Create request = new ReviewRequest.Create("내용");

		reviewService.createReview(
			testAccount.getId(),
			testRoute.getId(),
			request
		);

		// when
		reviewService.updateReview(
			testAccount.getId(),
			testAccount.getReviews().get(0).getId(),
			new ReviewRequest.Update("변경된내용")
		);

		// then
		Assertions.assertEquals("변경된내용",testAccount.getReviews().get(0).getContent());
		Assertions.assertEquals("변경된내용",testRoute.getReviews().get(0).getContent());
	}

	@Test
	void 리뷰조회_성공_멤버() {
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			testAccount.getId(),
			AccountRole.MEMBER
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		ReviewRequest.Create request = new ReviewRequest.Create("내용");

		reviewService.createReview(
			testAccount.getId(),
			testRoute.getId(),
			request
		);

		// when
		Page<ReviewResponse.Search> result = reviewService.getReviewsByMember(
			testAccount.getId(),
			testRoute.getId(),
			PageRequest.of(0,10)
		);

		// then
		Assertions.assertEquals("내용",result.getContent().get(0).content());
	}

	@Test
	void 리뷰조회_성공_어드민() {
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			testAccount.getId(),
			AccountRole.ADMIN
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		ReviewRequest.Create request = new ReviewRequest.Create("내용");

		reviewService.createReview(
			testAccount.getId(),
			testRoute.getId(),
			request
		);

		// when
		Page<ReviewResponse.Search> result = reviewService.getReviewsByAdmin(
			testRoute.getId(),
			PageRequest.of(0,10)
		);

		// then
		Assertions.assertEquals("내용",result.getContent().get(0).content());
	}

	@Test
	void 리뷰삭제_성공_멤버() {
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			testAccount.getId(),
			AccountRole.MEMBER
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		ReviewRequest.Create request = new ReviewRequest.Create("내용");

		reviewService.createReview(
			testAccount.getId(),
			testRoute.getId(),
			request
		);

		Assertions.assertNotNull(reviewRepository.findById(testAccount.getReviews().get(0).getId()).orElse(null));

		// when
		reviewService.deleteReviewByMember(
			testAccount.getId(),
			testAccount.getReviews().get(0).getId()
		);

		// then
		Assertions.assertNull(reviewRepository.findById(testAccount.getReviews().get(0).getId()).orElse(null));
	}

	@Test
	void 리뷰삭제_성공_어드민() {
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			testAccount.getId(),
			AccountRole.ADMIN
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		ReviewRequest.Create request = new ReviewRequest.Create("내용");

		reviewService.createReview(
			testAccount.getId(),
			testRoute.getId(),
			request
		);

		Assertions.assertNotNull(reviewRepository.findById(testAccount.getReviews().get(0).getId()).orElse(null));

		// when
		reviewService.deleteReviewByAdmin(testAccount.getReviews().get(0).getId());

		// then
		Assertions.assertNull(reviewRepository.findById(testAccount.getReviews().get(0).getId()).orElse(null));
	}
}