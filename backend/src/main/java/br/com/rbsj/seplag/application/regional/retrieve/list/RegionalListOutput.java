package br.com.rbsj.seplag.application.regional.retrieve.list;

import br.com.rbsj.seplag.domain.regional.Regional;

public record RegionalListOutput(
        String id,
        String nome,
        boolean ativo
) {
    public static RegionalListOutput from(Regional regional) {
        return new RegionalListOutput(
                regional.getId().getValue(),
                regional.getNome(),
                regional.isAtivo()
        );
    }
}
