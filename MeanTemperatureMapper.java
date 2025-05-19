import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MeanTemperatureMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
    boolean isHeader = true;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        if (isHeader && line.contains("time")) {
            isHeader = false;
            return;
        }

        String[] parts = line.split(",");
        for (int i = 1; i < parts.length; i++) {
            try {
                float temp = Float.parseFloat(parts[i]);
                context.write(new Text("temp"), new FloatWritable(temp));
            } catch (NumberFormatException ignored) {
            }
        }
    }
}
