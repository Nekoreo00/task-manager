import java.awt.Image;

import javax.swing.ImageIcon;

public class IconUtil {

    public static ImageIcon createIcon(
        String path,
        int width,
        int height
    ) {

        java.net.URL imageUrl =
            IconUtil.class.getResource(path);

        if (imageUrl == null) {

            System.out.println(
                "画像が見つかりません: " + path
            );

            return null;
        }

        ImageIcon icon =
            new ImageIcon(imageUrl);

        Image image =
            icon.getImage();

        Image resizedImage =
            image.getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
            );

        return new ImageIcon(
            resizedImage
        );
    }
}