package dev.scx.http.media.multi_part;

/// MultiPart
///
/// @author scx567888
/// @version 0.0.1
public interface MultiPart extends Iterable<MultiPartPart> {

    static MultiPartWritable of(String boundary) {
        return new MultiPartImpl(boundary);
    }

    static MultiPartWritable of() {
        return new MultiPartImpl("ScxBoundary" + MultiPartHelper.randomString(16));
    }

    String boundary();

}
