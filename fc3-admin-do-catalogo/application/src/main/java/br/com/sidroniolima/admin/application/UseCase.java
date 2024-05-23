package br.com.sidroniolima.admin.application;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN anIn);
}
