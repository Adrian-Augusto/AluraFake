package br.com.alura.AluraFake.task.entity;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("OPEN_TEXT")
public class NewOpenText extends Task{


}

