package org.elasticsearch.env;


import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;


/**
 * Dummy implementation of FileStore object.
 * Its a workaround to handle the exception thrown from
 * org.elasticsearch.env.Environment#getFileStore(java.nio.file.Path)
 * when running on a RAMDISK machine or similar file system
 *
 */
class ESDummyFileStore extends ESFileStore {

    ESDummyFileStore(FileStore in) {
        super(in);
    }


    @Override
    public String name() {
        return "FS_UNKNOWN_NAME";
    }

    @Override
    public String type() {
        return "FS_UNKNOWN_TYPE";
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public long getTotalSpace() throws IOException {
        return Long.MAX_VALUE;
    }

    @Override
    public long getUsableSpace() throws IOException {
        return Long.MAX_VALUE;
    }

    @Override
    public long getUnallocatedSpace() throws IOException {
        return Long.MAX_VALUE;
    }

    @Override
    public boolean supportsFileAttributeView(Class<? extends FileAttributeView> type) {
        return false;
    }

    @Override
    public boolean supportsFileAttributeView(String name) {
        if ("lucene".equals(name)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) {
        return null;
    }

    @Override
    public Object getAttribute(String attribute) throws IOException {
        switch (attribute) {
            // for the partition
            case "lucene:major_device_number":
                return -1;
            case "lucene:minor_device_number":
                return -1;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "FS_UNKNOWN";
    }
}
