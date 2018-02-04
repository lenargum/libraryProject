package materials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoMaterialTest {
    @Test
    void equals() {
        AudioVideoMaterial a = new AudioVideoMaterial("HJHLKJHK", "treuiyeryiuey, dufyhiuy", 123, 45, true);
        AudioVideoMaterial b = new AudioVideoMaterial("HJHLKJHK", "treuiyeryiuey, dufyhiuy", 124, 45, true);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        a.setTitle("hgj");
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertFalse(a.equals(a));
    }

}