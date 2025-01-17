package br.com.sidroniolima.admin.domain.events;

@FunctionalInterface
public interface DomainEventPublisher <T extends DomainEvent> {
    void publishEvent(T event);
}
