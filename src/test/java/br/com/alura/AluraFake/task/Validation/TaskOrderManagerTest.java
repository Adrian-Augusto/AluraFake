package br.com.alura.AluraFake.task.Validation;

import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskOrderManagerTest {

    private TaskOrderManager orderManager;

    @BeforeEach
    void setup() {
        orderManager = new TaskOrderManager();
    }

    @Test
    void prepararParaInsercao_reordenaEdeslocaCorretamente() {
        List<NewSingleChoice> tasks = new ArrayList<>();
        tasks.add(createTask(3));
        tasks.add(createTask(1));
        tasks.add(createTask(2));

        orderManager.prepararParaInsercao(tasks, 2);

        assertEquals(1, tasks.get(0).getOrderr());
        assertEquals(3, tasks.get(1).getOrderr());
        assertEquals(4, tasks.get(2).getOrderr());
    }

    @Test
    void prepararParaInsercao_lancaExceptionQuandoNovaOrdemInvalida() {
        List<NewSingleChoice> tasks = new ArrayList<>();
        tasks.add(createTask(1));
        tasks.add(createTask(2));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            orderManager.prepararParaInsercao(tasks, 4); // inválido
        });

        assertEquals("Ordem inválida: deve ser contínua.", ex.getMessage());
    }

    @Test
    void inserirTaskNaOrdem_insereCorretamente() {
        List<NewSingleChoice> tasks = new ArrayList<>();
        tasks.add(createTask(1));
        tasks.add(createTask(3));
        tasks.add(createTask(2));

        NewSingleChoice novaTask = createTask(0); // ordem será definida

        orderManager.inserirTaskNaOrdem(tasks, novaTask, 2);

        assertEquals(2, novaTask.getOrderr());

        tasks.sort((t1, t2) -> Integer.compare(t1.getOrderr(), t2.getOrderr()));

        assertEquals(1, tasks.get(0).getOrderr());
        assertEquals(2, tasks.get(1).getOrderr());
        assertEquals(3, tasks.get(2).getOrderr());
        assertEquals(4, tasks.get(3).getOrderr());
    }

    @Test
    void inserirTaskNaOrdem_lancaExceptionOrdemInvalida() {
        List<NewSingleChoice> tasks = new ArrayList<>();
        tasks.add(createTask(1));
        tasks.add(createTask(2));

        NewSingleChoice novaTask = createTask(0);

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            orderManager.inserirTaskNaOrdem(tasks, novaTask, 0);
        });
        assertTrue(ex1.getMessage().contains("Ordem inválida"));

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            orderManager.inserirTaskNaOrdem(tasks, novaTask, 4); // inválido
        });
        assertTrue(ex2.getMessage().contains("Ordem inválida"));
    }

    private NewSingleChoice createTask(int order) {
        NewSingleChoice task = new NewSingleChoice();
        task.setOrderr(order);
        return task;
    }
}
