package com.ftn.micro1.security;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
public class SecurityFilter  extends Filter<ILoggingEvent> {
	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getMessage().contains("SECURITY_EVENT")) {
		      return FilterReply.ACCEPT;
		} else {
		      return FilterReply.DENY;
		}
	}

}
