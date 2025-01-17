package br.com.sidroniolima.admin.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN anIn);
}
