package com.uos.all4runner.repository.account;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.domain.dto.response.QAccountResponse_Search;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.account.QAccount;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	private final QAccount qAccount = QAccount.account;

	@Override
	public Page<AccountResponse.Search> searchAccounts(
		String name,
		Pageable pageable
	) {

		List<AccountResponse.Search> result = queryFactory
			.select(new QAccountResponse_Search(
				qAccount.id,
				qAccount.name,
				qAccount.role,
				qAccount.status
			))
			.from(qAccount)
			.where(containsAccountName(name))
			.orderBy(qAccount.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		int total = queryFactory
			.select(qAccount.id)
			.from(qAccount)
			.where(containsAccountName(name))
			.fetch().size();

		return new PageImpl<>(result,pageable,total);
	}

	public BooleanExpression containsAccountName(String name){
		return (Strings.isNotBlank(name))? qAccount.name.containsIgnoreCase(name) : null;
	}
}
