import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MeanTemperatureStateMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
    private static final String HEADER_PREFIX = "time";
    private String[] headers;
    private boolean isHeaderParsed = false;

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        if (!isHeaderParsed && line.toLowerCase().startsWith(HEADER_PREFIX)) {
            headers = line.split(",");
            isHeaderParsed = true;
            return;
        }

        if (!isHeaderParsed || headers == null) return;

        String[] parts = line.split(",");
        if (parts.length != headers.length) return;

        for (int i = 1; i < parts.length; i++) {
            try {
                float temp = Float.parseFloat(parts[i]);
                String state = headers[i].replace("_temp", "").trim().toLowerCase();

                context.write(new Text(state), new FloatWritable(temp));
            } catch (NumberFormatException ignored) {}
        }
    }
}
