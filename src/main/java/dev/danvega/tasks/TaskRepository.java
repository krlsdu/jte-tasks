package dev.danvega.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Repository
public class TaskRepository {

    Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    @Autowired
    RestTemplate restClient;

    private final List<Task> tasks = new ArrayList<>();

    public TaskRepository() {

    }

    public List<Task> findAll() {
        ResponseEntity<CollectionModel<Person>> result = restClient.exchange("/persons", HttpMethod.GET,
                null,
                new CollectionModelType<Person>() {
                });

        Optional<Collection<Person>> content = Optional.ofNullable(result.getBody().getContent());
        content.ifPresent(persons -> {
            persons.forEach(person -> {
                tasks.add(new Task(String.valueOf(person.getId()), person.getFirstName()));
            });
        });
        return tasks;
    }

    public void create(Task task) {
        tasks.add(task);
    }

    public boolean remove(String id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }

    @PostConstruct
    private void init() {

        tasks.addAll(List.of(
                new Task(UUID.randomUUID().toString(), "Complete project proposal"),
                new Task(UUID.randomUUID().toString(), "Review code changes"),
                new Task(UUID.randomUUID().toString(), "Attend team meeting"),
                new Task(UUID.randomUUID().toString(), "Update documentation"),
                new Task(UUID.randomUUID().toString(), "Fix reported bugs"),
                new Task(UUID.randomUUID().toString(), "Prepare presentation slides"),
                new Task(UUID.randomUUID().toString(), "Respond to client emails"),
                new Task(UUID.randomUUID().toString(), "Run unit tests"),
                new Task(UUID.randomUUID().toString(), "Refactor legacy code"),
                new Task(UUID.randomUUID().toString(), "Plan next sprint")));
    }
}
