package br.com.alura.AluraFake.task.Validation;

import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.entity.Task;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class TaskOrderManager {

    /**
     * Reordena as tasks existentes e desloca as que têm ordem >= novaOrdem.
     *
     * @param tasks      Lista de todas as tasks do curso
     * @param novaOrdem  A ordem onde a nova task será inserida
     * @throws IllegalArgumentException se novaOrdem for inválida
     */
    public void prepararParaInsercao(List<NewSingleChoice> tasks, Integer novaOrdem) {

        // 1. Reordenar as tasks em sequência contínua
        tasks.sort(Comparator.comparingInt(Task::getOrderr));
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setOrderr(i + 1);
        }

        // 2. Validar se nova ordem é permitida
        if (novaOrdem > tasks.size() + 1) {
            throw new IllegalArgumentException("Ordem inválida: deve ser contínua.");
        }

        // 3. Deslocar as tasks com ordem >= novaOrdem
        for (Task task : tasks) {
            if (task.getOrderr() >= novaOrdem) {
                task.setOrderr(task.getOrderr() + 1);
            }
        }
    }
    public void inserirTaskNaOrdem(List<NewSingleChoice> tasks, NewSingleChoice novaTask, Integer novaOrdem) {
        // 1. Reordenar a lista
        tasks.sort(Comparator.comparingInt(Task::getOrderr));
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setOrderr(i + 1);
        }

        // 2. Validar ordem
        if (novaOrdem > tasks.size() + 1 || novaOrdem < 1) {
            throw new IllegalArgumentException("Ordem inválida: deve estar entre 1 e " + (tasks.size() + 1));
        }

        // 3. Deslocar tasks
        for (Task task : tasks) {
            if (task.getOrderr() >= novaOrdem) {
                task.setOrderr(task.getOrderr() + 1);
            }
        }

        // 4. Inserir nova task com a ordem correta
        novaTask.setOrderr(novaOrdem);
        tasks.add(novaTask);

        // 5. Reordenar por ordem final (opcional)
        tasks.sort(Comparator.comparingInt(Task::getOrderr));
    }
}
