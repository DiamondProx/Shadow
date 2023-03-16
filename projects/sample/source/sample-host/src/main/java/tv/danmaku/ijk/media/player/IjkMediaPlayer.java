////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tv.danmaku.ijk.media.player;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.res.AssetFileDescriptor;
//import android.graphics.Rect;
//import android.media.MediaCodecInfo;
//import android.media.MediaCodecList;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.os.ParcelFileDescriptor;
//import android.os.PowerManager;
//import android.os.Build.VERSION;
//import android.os.PowerManager.WakeLock;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Surface;
//import android.view.SurfaceHolder;
//import java.io.FileDescriptor;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//import java.lang.reflect.Field;
//import java.security.InvalidParameterException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Map.Entry;
//public final class IjkMediaPlayer  {
//
//    private SurfaceHolder mSurfaceHolder;
//    private IjkMediaPlayer.EventHandler mEventHandler;
//    private WakeLock mWakeLock;
//    private boolean mScreenOnWhilePlaying;
//    private boolean mStayAwake;
//    private int mVideoWidth;
//    private int mVideoHeight;
//    private int mVideoSarNum;
//    private int mVideoSarDen;
//    private String mDataSource;
//    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
//        public void loadLibrary(String libName) throws UnsatisfiedLinkError, SecurityException {
//            System.loadLibrary(libName);
//        }
//    };
//    private static volatile boolean mIsLibLoaded = false;
//    private static volatile boolean mIsNativeInitialized = false;
//    private IjkMediaPlayer.OnControlMessageListener mOnControlMessageListener;
//    private IjkMediaPlayer.OnNativeInvokeListener mOnNativeInvokeListener;
//
//    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
//        Class var1 = IjkMediaPlayer.class;
//        synchronized(IjkMediaPlayer.class) {
//            if (!mIsLibLoaded) {
//                if (libLoader == null) {
//                    libLoader = sLocalLibLoader;
//                }
//
//                libLoader.loadLibrary("ijkffmpeg");
////                libLoader.loadLibrary("ijksdl");
////                libLoader.loadLibrary("ijkplayer");
//                mIsLibLoaded = true;
//            }
//
//        }
//    }
//
//    private static void initNativeOnce() {
//        Class var0 = IjkMediaPlayer.class;
//        synchronized(IjkMediaPlayer.class) {
//            if (!mIsNativeInitialized) {
//                native_init();
//                mIsNativeInitialized = true;
//            }
//
//        }
//    }
//
//    public IjkMediaPlayer() {
//        this(sLocalLibLoader);
//    }
//
//    public IjkMediaPlayer(IjkLibLoader libLoader) {
//        this.mWakeLock = null;
//        this.initPlayer(libLoader);
//    }
//
//    private void initPlayer(IjkLibLoader libLoader) {
//        loadLibrariesOnce(libLoader);
//        initNativeOnce();
//        Looper looper;
//        if ((looper = Looper.myLooper()) != null) {
//            this.mEventHandler = new IjkMediaPlayer.EventHandler(this, looper);
//        } else if ((looper = Looper.getMainLooper()) != null) {
//            this.mEventHandler = new IjkMediaPlayer.EventHandler(this, looper);
//        } else {
//            this.mEventHandler = null;
//        }
//
//        this.native_setup(new WeakReference(this));
//    }
//
//    private native void _setFrameAtTime(String var1, long var2, long var4, int var6, int var7) throws IllegalArgumentException, IllegalStateException;
//
//    private native void _setVideoSurface(Surface var1);
//
//    public void setDisplay(SurfaceHolder sh) {
//        this.mSurfaceHolder = sh;
//        Surface surface;
//        if (sh != null) {
//            surface = sh.getSurface();
//        } else {
//            surface = null;
//        }
//
//        this._setVideoSurface(surface);
//        this.updateSurfaceScreenOn();
//    }
//
//    public void setSurface(Surface surface) {
//        if (this.mScreenOnWhilePlaying && surface != null) {
//        }
//
//        this.mSurfaceHolder = null;
//        this._setVideoSurface(surface);
//        this.updateSurfaceScreenOn();
//    }
//
//    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
//        this.setDataSource(context, uri, (Map)null);
//    }
//
//    @TargetApi(14)
//    public void setDataSource(Context context, Uri uri, Map<String, String> headers) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
//        String scheme = uri.getScheme();
//        if ("file".equals(scheme)) {
//            this.setDataSource(uri.getPath());
//        } else {
//            if ("content".equals(scheme) && "settings".equals(uri.getAuthority())) {
//                uri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.getDefaultType(uri));
//                if (uri == null) {
//                    throw new FileNotFoundException("Failed to resolve default ringtone");
//                }
//            }
//
//            AssetFileDescriptor fd = null;
//
//            label114: {
//                try {
//                    ContentResolver resolver = context.getContentResolver();
//                    fd = resolver.openAssetFileDescriptor(uri, "r");
//                    if (fd != null) {
//                        if (fd.getDeclaredLength() < 0L) {
//                            this.setDataSource(fd.getFileDescriptor());
//                        } else {
//                            this.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getDeclaredLength());
//                        }
//
//                        return;
//                    }
//                } catch (SecurityException var11) {
//                    break label114;
//                } catch (IOException var12) {
//                    break label114;
//                } finally {
//                    if (fd != null) {
//                        fd.close();
//                    }
//
//                }
//
//                return;
//            }
//
//            this.setDataSource(uri.toString(), headers);
//        }
//    }
//
//    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
//        this.mDataSource = path;
//        this._setDataSource(path, (String[])null, (String[])null);
//    }
//
//    public void setDataSource(String path, Map<String, String> headers) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
//        if (headers != null && !headers.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            Iterator var4 = headers.entrySet().iterator();
//
//            while(var4.hasNext()) {
//                Entry<String, String> entry = (Entry)var4.next();
//                sb.append((String)entry.getKey());
//                sb.append(":");
//                String value = (String)entry.getValue();
//                if (!TextUtils.isEmpty(value)) {
//                    sb.append((String)entry.getValue());
//                }
//
//                sb.append("\r\n");
//                this.setOption(1, "headers", sb.toString());
//                this.setOption(1, "protocol_whitelist", "async,cache,crypto,file,http,https,ijkhttphook,ijkinject,ijklivehook,ijklongurl,ijksegment,ijktcphook,pipe,rtp,tcp,tls,udp,ijkurlhook,data");
//            }
//        }
//
//        this.setDataSource(path);
//    }
//
//    @TargetApi(13)
//    public void setDataSource(FileDescriptor fd) throws IOException, IllegalArgumentException, IllegalStateException {
//        if (VERSION.SDK_INT < 12) {
//            boolean var2 = true;
//
//            int native_fd;
//            try {
//                Field f = fd.getClass().getDeclaredField("descriptor");
//                f.setAccessible(true);
//                native_fd = f.getInt(fd);
//            } catch (NoSuchFieldException var9) {
//                throw new RuntimeException(var9);
//            } catch (IllegalAccessException var10) {
//                throw new RuntimeException(var10);
//            }
//
//            this._setDataSourceFd(native_fd);
//        } else {
//            ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(fd);
//
//            try {
//                this._setDataSourceFd(pfd.getFd());
//            } finally {
//                pfd.close();
//            }
//        }
//
//    }
//
//    private void setDataSource(FileDescriptor fd, long offset, long length) throws IOException, IllegalArgumentException, IllegalStateException {
//        this.setDataSource(fd);
//    }
//
//
//
//
//    private native void _setDataSource(String var1, String[] var2, String[] var3) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
//
//    private native void _setDataSourceFd(int var1) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
//
//    public String getDataSource() {
//        return this.mDataSource;
//    }
//
//    public void prepareAsync() throws IllegalStateException {
//        this._prepareAsync();
//    }
//
//    public native void _prepareAsync() throws IllegalStateException;
//
//    public void start() throws IllegalStateException {
//        this.stayAwake(true);
//        this._start();
//    }
//
//    private native void _start() throws IllegalStateException;
//
//    public void stop() throws IllegalStateException {
//        this.stayAwake(false);
//        this._stop();
//    }
//
//    private native void _stop() throws IllegalStateException;
//
//    public void pause() throws IllegalStateException {
//        this.stayAwake(false);
//        this._pause();
//    }
//
//    private native void _pause() throws IllegalStateException;
//
//
//
//    @SuppressLint({"Wakelock"})
//    private void stayAwake(boolean awake) {
//        if (this.mWakeLock != null) {
//            if (awake && !this.mWakeLock.isHeld()) {
//                this.mWakeLock.acquire();
//            } else if (!awake && this.mWakeLock.isHeld()) {
//                this.mWakeLock.release();
//            }
//        }
//
//        this.mStayAwake = awake;
//        this.updateSurfaceScreenOn();
//    }
//
//    private void updateSurfaceScreenOn() {
//        if (this.mSurfaceHolder != null) {
//            this.mSurfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
//        }
//
//    }
//
//
//
//    public int getSelectedTrack(int trackType) {
//        switch(trackType) {
//            case 1:
//                return (int)this._getPropertyLong(20001, -1L);
//            case 2:
//                return (int)this._getPropertyLong(20002, -1L);
//            case 3:
//                return (int)this._getPropertyLong(20011, -1L);
//            default:
//                return -1;
//        }
//    }
//
//    public void selectTrack(int track) {
//        this._setStreamSelected(track, true);
//    }
//
//    public void deselectTrack(int track) {
//        this._setStreamSelected(track, false);
//    }
//
//    private native void _setStreamSelected(int var1, boolean var2);
//
//    public int getVideoWidth() {
//        return this.mVideoWidth;
//    }
//
//    public int getVideoHeight() {
//        return this.mVideoHeight;
//    }
//
//    public int getVideoSarNum() {
//        return this.mVideoSarNum;
//    }
//
//    public int getVideoSarDen() {
//        return this.mVideoSarDen;
//    }
//
//    public native boolean isPlaying();
//
//    public native void seekTo(long var1) throws IllegalStateException;
//
//    public native long getCurrentPosition();
//    public native long getDuration();
//
//    public void release() {
//        this.stayAwake(false);
//        this.updateSurfaceScreenOn();
//        this._release();
//    }
//
//    private native void _release();
//
//    public void reset() {
//        this.stayAwake(false);
//        this._reset();
//        this.mEventHandler.removeCallbacksAndMessages((Object)null);
//        this.mVideoWidth = 0;
//        this.mVideoHeight = 0;
//    }
//
//    private native void _reset();
//
//    public void setLooping(boolean looping) {
//        int loopCount = looping ? 0 : 1;
//        this.setOption(4, "loop", (long)loopCount);
//        this._setLoopCount(loopCount);
//    }
//
//    private native void _setLoopCount(int var1);
//
//    public boolean isLooping() {
//        int loopCount = this._getLoopCount();
//        return loopCount != 1;
//    }
//
//    private native int _getLoopCount();
//
//    public void setSpeed(float speed) {
//        this._setPropertyFloat(10003, speed);
//    }
//
//    public float getSpeed(float speed) {
//        return this._getPropertyFloat(10003, 0.0F);
//    }
//
//    public int getVideoDecoder() {
//        return (int)this._getPropertyLong(20003, 0L);
//    }
//
//    public float getVideoOutputFramesPerSecond() {
//        return this._getPropertyFloat(10002, 0.0F);
//    }
//
//    public float getVideoDecodeFramesPerSecond() {
//        return this._getPropertyFloat(10001, 0.0F);
//    }
//
//    public long getVideoCachedDuration() {
//        return this._getPropertyLong(20005, 0L);
//    }
//
//    public long getAudioCachedDuration() {
//        return this._getPropertyLong(20006, 0L);
//    }
//
//    public long getVideoCachedBytes() {
//        return this._getPropertyLong(20007, 0L);
//    }
//
//    public long getAudioCachedBytes() {
//        return this._getPropertyLong(20008, 0L);
//    }
//
//    public long getVideoCachedPackets() {
//        return this._getPropertyLong(20009, 0L);
//    }
//
//    public long getAudioCachedPackets() {
//        return this._getPropertyLong(20010, 0L);
//    }
//
//    public long getAsyncStatisticBufBackwards() {
//        return this._getPropertyLong(20201, 0L);
//    }
//
//    public long getAsyncStatisticBufForwards() {
//        return this._getPropertyLong(20202, 0L);
//    }
//
//    public long getAsyncStatisticBufCapacity() {
//        return this._getPropertyLong(20203, 0L);
//    }
//
//    public long getTrafficStatisticByteCount() {
//        return this._getPropertyLong(20204, 0L);
//    }
//
//    public long getCacheStatisticPhysicalPos() {
//        return this._getPropertyLong(20205, 0L);
//    }
//
//    public long getCacheStatisticFileForwards() {
//        return this._getPropertyLong(20206, 0L);
//    }
//
//    public long getCacheStatisticFilePos() {
//        return this._getPropertyLong(20207, 0L);
//    }
//
//    public long getCacheStatisticCountBytes() {
//        return this._getPropertyLong(20208, 0L);
//    }
//
//    public long getFileSize() {
//        return this._getPropertyLong(20209, 0L);
//    }
//
//    public long getBitRate() {
//        return this._getPropertyLong(20100, 0L);
//    }
//
//    public long getTcpSpeed() {
//        return this._getPropertyLong(20200, 0L);
//    }
//
//    public long getSeekLoadDuration() {
//        return this._getPropertyLong(20300, 0L);
//    }
//
//    private native float _getPropertyFloat(int var1, float var2);
//
//    private native void _setPropertyFloat(int var1, float var2);
//
//    private native long _getPropertyLong(int var1, long var2);
//
//    private native void _setPropertyLong(int var1, long var2);
//
//    public float getDropFrameRate() {
//        return this._getPropertyFloat(10007, 0.0F);
//    }
//
//    public native void setVolume(float var1, float var2);
//
//    public native int getAudioSessionId();
//
//
//    public void setLogEnabled(boolean enable) {
//    }
//
//    public boolean isPlayable() {
//        return true;
//    }
//
//    private native String _getVideoCodecInfo();
//
//    private native String _getAudioCodecInfo();
//
//    public void setOption(int category, String name, String value) {
//        this._setOption(category, name, value);
//    }
//
//    public void setOption(int category, String name, long value) {
//        this._setOption(category, name, value);
//    }
//
//    private native void _setOption(int var1, String var2, String var3);
//
//    private native void _setOption(int var1, String var2, long var3);
//
//    public Bundle getMediaMeta() {
//        return this._getMediaMeta();
//    }
//
//    private native Bundle _getMediaMeta();
//
//    public static String getColorFormatName(int mediaCodecColorFormat) {
//        return _getColorFormatName(mediaCodecColorFormat);
//    }
//
//    private static native String _getColorFormatName(int var0);
//
//    public void setAudioStreamType(int streamtype) {
//    }
//
//    public void setKeepInBackground(boolean keepInBackground) {
//    }
//
//    private static native void native_init();
//
//    private native void native_setup(Object var1);
//
//    private native void native_finalize();
//
//    private native void native_message_loop(Object var1);
//
//    protected void finalize() throws Throwable {
//        super.finalize();
//        this.native_finalize();
//    }
//
//    public void setCacheShare(int share) {
//        this._setPropertyLong(20210, (long)share);
//    }
//
//    private static void postEventFromNative(Object weakThiz, int what, int arg1, int arg2, Object obj) {
//        if (weakThiz != null) {
//            IjkMediaPlayer mp = (IjkMediaPlayer)((WeakReference)weakThiz).get();
//            if (mp != null) {
//                if (what == 200 && arg1 == 2) {
//                    mp.start();
//                }
//
//                if (mp.mEventHandler != null) {
//                    Message m = mp.mEventHandler.obtainMessage(what, arg1, arg2, obj);
//                    mp.mEventHandler.sendMessage(m);
//                }
//
//            }
//        }
//    }
//
//    public void setOnControlMessageListener(IjkMediaPlayer.OnControlMessageListener listener) {
//        this.mOnControlMessageListener = listener;
//    }
//
//    public void setOnNativeInvokeListener(IjkMediaPlayer.OnNativeInvokeListener listener) {
//        this.mOnNativeInvokeListener = listener;
//    }
//
//
//
//
//
//    public static native void native_profileBegin(String var0);
//
//    public static native void native_profileEnd();
//
//    public static native void native_setLogLevel(int var0);
//
//
//
//
//    public interface OnNativeInvokeListener {
//        int CTRL_WILL_TCP_OPEN = 131073;
//        int CTRL_DID_TCP_OPEN = 131074;
//        int CTRL_WILL_HTTP_OPEN = 131075;
//        int CTRL_WILL_LIVE_OPEN = 131077;
//        int CTRL_WILL_CONCAT_RESOLVE_SEGMENT = 131079;
//        int EVENT_WILL_HTTP_OPEN = 1;
//        int EVENT_DID_HTTP_OPEN = 2;
//        int EVENT_WILL_HTTP_SEEK = 3;
//        int EVENT_DID_HTTP_SEEK = 4;
//        String ARG_URL = "url";
//        String ARG_SEGMENT_INDEX = "segment_index";
//        String ARG_RETRY_COUNTER = "retry_counter";
//        String ARG_ERROR = "error";
//        String ARG_FAMILIY = "family";
//        String ARG_IP = "ip";
//        String ARG_PORT = "port";
//        String ARG_FD = "fd";
//        String ARG_OFFSET = "offset";
//        String ARG_HTTP_CODE = "http_code";
//
//        boolean onNativeInvoke(int var1, Bundle var2);
//    }
//
//    public interface OnControlMessageListener {
//        String onControlResolveSegmentUrl(int var1);
//    }
//
//    private static class EventHandler extends Handler {
//        private final WeakReference<IjkMediaPlayer> mWeakPlayer;
//
//        public EventHandler(IjkMediaPlayer mp, Looper looper) {
//            super(looper);
//            this.mWeakPlayer = new WeakReference(mp);
//        }
//
//    }
//}
