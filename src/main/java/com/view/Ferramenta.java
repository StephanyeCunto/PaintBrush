package com.view;

import lombok.Getter;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public enum Ferramenta {
    LAPIS("Lápis"),
    BORRACHA("Borracha"),
    LINHA("Linha"),
    CIRCULO("Circulo"),
    RETANGULO("Retângulo"),
    CILINDRO("Cilindro"),
    PIRAMIDE("Pirâmide"),
    SPRAY("Spray");

    private final String nome;

    private static final Set<Ferramenta> FORMAS = Collections.unmodifiableSet(EnumSet.of(CIRCULO, RETANGULO, CILINDRO, PIRAMIDE));
    private static final Set<Ferramenta> FORMAS_PREENCHIDAS = Collections.unmodifiableSet(EnumSet.of(CIRCULO, RETANGULO));
    private static final Map<String, Ferramenta> MAPA_POR_NOME = Arrays.stream(values())
            .collect(Collectors.toMap(f -> f.nome.toLowerCase(), f -> f));

    Ferramenta(String nome) {
        this.nome = nome;
    }

    public static Ferramenta porNome(String nome) {
        Ferramenta ferramenta = MAPA_POR_NOME.get(nome.toLowerCase());
        if (ferramenta == null) {
            throw new IllegalArgumentException("Ferramenta não encontrada: " + nome);
        }
        return ferramenta;
    }

    public boolean ehPreenchida() {
        return FORMAS_PREENCHIDAS.contains(this);
    }

    public boolean ehForma() {
        return FORMAS.contains(this);
    }
}
