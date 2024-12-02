package com.ms.email.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class DomainEvents {
  private static final Map<String, List<Consumer<IDomainEvent>>> handlersMap = new ConcurrentHashMap<>();
  private static final List<AggregateRoot<?>> markedAggregates = new ArrayList<>();

  public static <T extends IDomainEvent> void register(Class<T> eventClass, Consumer<T> handler) {
    handlersMap.computeIfAbsent(eventClass.getSimpleName(), k -> new ArrayList<>())
        .add((Consumer<IDomainEvent>) handler);
  }

  public static void markAggregateForDispatch(AggregateRoot<?> aggregate) {
    if (findMarkedAggregateById(aggregate.getId()) == null) {
      markedAggregates.add(aggregate);
    }
  }

  public static void dispatchAggregateEvents(UUID aggregateId) {
    AggregateRoot<?> aggregate = findMarkedAggregateById(aggregateId);
    if (aggregate != null) {
      dispatchAggregateEvents(aggregate);
      aggregate.clearEvents();
      removeAggregateFromMarkedDispatchList(aggregate);
    }
  }

  private static void dispatchAggregateEvents(AggregateRoot<?> aggregate) {
    aggregate.getDomainEvents().forEach(DomainEvents::dispatch);
  }

  private static void dispatch(IDomainEvent event) {
    String eventName = event.getClass().getSimpleName();
    List<Consumer<IDomainEvent>> handlers = handlersMap.get(eventName);
    if (handlers != null) {
      handlers.forEach(handler -> handler.accept(event));
    }
  }

  private static AggregateRoot<?> findMarkedAggregateById(UUID id) {
    return markedAggregates.stream()
        .filter(aggregate -> aggregate.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  private static void removeAggregateFromMarkedDispatchList(AggregateRoot<?> aggregate) {
    markedAggregates.removeIf(a -> a.getId().equals(aggregate.getId()));
  }

  public static void clearHandlers() {
    handlersMap.clear();
  }

  public static void clearMarkedAggregates() {
    markedAggregates.clear();
  }
}
