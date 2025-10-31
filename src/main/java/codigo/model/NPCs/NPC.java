package codigo.model.NPCs;

public abstract class NPC {

    private int id;
    private int puntos;

    // ==========================
    // CONSTRUCTORES
    // ==========================

    public NPC(int id, int puntos) {
        this.id = id;
        this.puntos = puntos;
    }
}
