package dev.scx.http.media.multi_part;

import java.util.concurrent.ThreadLocalRandom;

/// MultiPartHelper
///
/// @author scx567888
/// @version 0.0.1
final class MultiPartHelper {

    private static final char[] NUMBER_AND_LETTER = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String randomString(int size) {
        var random = ThreadLocalRandom.current();
        var value = new char[size];
        int length = NUMBER_AND_LETTER.length;
        for (int i = 0; i < size; i = i + 1) {
            value[i] = NUMBER_AND_LETTER[random.nextInt(length)];
        }
        return new String(value);
    }

}
