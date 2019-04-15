package cn.com.compass.cache.redis.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayOutputStream;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 
 * @date 2018年8月5日 上午12:23:14
 *
 */
@Slf4j
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private static final ThreadLocal<Kryo> kryos = ThreadLocal.withInitial(Kryo::new);

    private Class<T> clazz;

    public KryoRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return EMPTY_BYTE_ARRAY;
        }

        Kryo kryo = kryos.get();
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        kryo.setReferences(false);
        kryo.register(clazz);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Output output = new Output(baos,100000)) {
            // 缓存字节数 默认4096 实际对象超出大小的时候，系列化的时候并不会报告错误，但是系列化结果已经不完整，从而导致反系列化的时候会失败
            kryo.writeClassAndObject(output, t);
            output.flush();
            return baos.toByteArray();
        } catch (Exception e) {
        	log.error("serialization exception:{}",e);
        }
        return EMPTY_BYTE_ARRAY;
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        Kryo kryo = kryos.get();
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        kryo.setReferences(false);
        kryo.register(clazz);

        try (Input input = new Input(bytes)) {
            return (T) kryo.readClassAndObject(input);
        } catch (Exception e) {
        	log.error("serialization exception:{}",e);
        	throw new SerializationException("serialization exception",e);
        }
    }
}