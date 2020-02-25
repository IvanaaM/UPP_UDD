package com.ftn.micro1.security;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class NeporFilter extends Filter<ILoggingEvent> {
	@Override
	public FilterReply decide(ILoggingEvent event) {
		// TODO Auto-generated method stub
		if (event.getMessage().contains("NEPOR_EVENT")) {
		      return FilterReply.ACCEPT;
		} else {
		      return FilterReply.DENY;
		}
	}
}
