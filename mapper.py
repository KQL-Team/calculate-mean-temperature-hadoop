import sys
import csv

reader = csv.reader(sys.stdin)
header = next(reader)

for row in reader:
    for i in range(1, len(row)):
        try:
            temp = float(row[i])
            print(f"temp\t{temp}\t1")
        except:
            continue