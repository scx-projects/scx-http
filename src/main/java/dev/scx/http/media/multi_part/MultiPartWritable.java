package dev.scx.http.media.multi_part;

import java.io.File;
import java.io.FileNotFoundException;

/// MultiPartWritable
///
/// @author scx567888
/// @version 0.0.1
public interface MultiPartWritable extends MultiPart {

    MultiPartWritable boundary(String boundary);

    MultiPartWritable add(MultiPartPart part);

    default MultiPartWritable add(String name, String value) {
        return add(MultiPartPart.of(name, value));
    }

    default MultiPartWritable add(String name, byte[] value) {
        return add(MultiPartPart.of(name, value));
    }

    default MultiPartWritable add(String name, File value) throws FileNotFoundException {
        return add(MultiPartPart.of(name, value));
    }

}
