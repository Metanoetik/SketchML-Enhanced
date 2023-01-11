package org.dma.sketchml.sketch.base;

public class SketchMLException extends RuntimeException {
    public SketchMLException(String message) {
        super(message);
    }

    public SketchMLException(Throwable cause) {
        super(cause);
    }