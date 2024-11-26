package br.com.sidroniolima.admin.infrastructure.services;

public interface EventService {
    //void send(DomainEvent event);
    void send(Object event); // para torná-la genérica
}
