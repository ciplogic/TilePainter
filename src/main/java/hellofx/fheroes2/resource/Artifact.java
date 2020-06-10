package hellofx.fheroes2.resource;

public class Artifact {
    int id;
    int ext;

    public Artifact(int art) {

        id = Math.min(art, ArtifactKind.UNKNOWN);
    }

    public Artifact() {

    }
}
