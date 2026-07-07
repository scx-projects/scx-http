package dev.scx.http.media.multi_part;

import static dev.scx.random.ScxRandom.randomString;

/// MultiPart
///
/// @author scx567888
public interface MultiPart extends Iterable<MultiPartPart> {

    static MultiPartWritable of(String boundary) {
        return new MultiPartImpl(boundary);
    }

    static MultiPartWritable of() {
        return new MultiPartImpl("ScxBoundary" + randomString(16));
    }

    String boundary();

}
