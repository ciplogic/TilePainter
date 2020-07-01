package hellofx.fheroes2.resource;

import hellofx.fheroes2.agg.Bitmap;

import static hellofx.fheroes2.resource.ArtifactLevel.ART_ULTIMATE;

public class UltimateArtifact extends Artifact {
    int index;
    Bitmap puzzlemap;
    boolean isfound;

    void Set(int pos, Artifact a) {
        var art = this;
        art.id = a.IsValid() ? a.id : Rand(ART_ULTIMATE);
        index = pos;
        isfound = false;

        MakeSurface();
    }

    private void MakeSurface() {
    }

}
