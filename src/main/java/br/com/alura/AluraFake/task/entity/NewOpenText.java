package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class NewOpenText extends Task{
    protected Type type=Type.OPEN_TEXT;

}

