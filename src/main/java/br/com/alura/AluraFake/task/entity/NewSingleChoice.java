package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("SINGLE")
public class NewSingleChoice extends Task {

    private Type type = Type.SINGLE_CHOICE;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Options> options = new ArrayList<>();
}