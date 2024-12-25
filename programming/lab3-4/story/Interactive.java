package story;
// изменить интерфейс взаимодействия с неким любым предметом (будь то шкаф, окно, или, может, коротышка) E to interact
// SOLID вчитаться, конкретно S и I. Суть в однои методе для АБСОЛЮТНО РАЗНЫХ ОБЪЕКТОВ
public interface Interactive {
    void Interact(AbstractCreature creature);
}
