package com.uos.all4runner.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LinkType {
	BRIDGE("다리/육교"),
	CROSSWALK("횡단보도"),
	FOOTPATH("보행도로");
	private final String description;
}
