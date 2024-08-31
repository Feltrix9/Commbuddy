package com.example.prueba1;

import java.util.ArrayList;
import java.util.List;

public class PersonalizadoRepository {

    private static PersonalizadoRepository instance;
    private List<Personalizado> personalizados;

    private PersonalizadoRepository() {
        personalizados = new ArrayList<>();
    }

    public static PersonalizadoRepository getInstance() {
        if (instance == null) {
            instance = new PersonalizadoRepository();
        }
        return instance;
    }

    public void addPersonalizado(Personalizado personalizado) {
        personalizados.add(personalizado);
    }

    public List<Personalizado> getPersonalizados() {
        return new ArrayList<>(personalizados);
    }
}
