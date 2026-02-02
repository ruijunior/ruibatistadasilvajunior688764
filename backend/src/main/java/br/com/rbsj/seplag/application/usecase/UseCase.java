package br.com.rbsj.seplag.application.usecase;

/**
 * Interface base para casos de uso.
 * Define o contrato para execução de casos de uso da aplicação.
 */
public interface UseCase<I, O> {
    
    /**
     * Executa o caso de uso.
     * 
     * @param input dados de entrada
     * @return resultado da execução
     */
    O execute(I input);
}
