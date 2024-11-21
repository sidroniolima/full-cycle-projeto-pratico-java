package br.com.sidroniolima.admin.domain.events;

import java.io.Serializable;
import java.time.Instant;

public interface DomainEvent extends Serializable {
    Instant occorredOn();
}
