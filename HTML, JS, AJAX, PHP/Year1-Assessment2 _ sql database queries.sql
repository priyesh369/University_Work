# 1

DESCRIBE databases;

# =========

# 2

DESCRIBE translation;

# =========

# 3

SELECT COUNT(*) FROM transcript WHERE status = 'known';

# =========

# 4

SELECT description FROM gene WHERE gene_id = '9240';

# =========

# 5

SELECT SUM(length) FROM seq_region WHERE seq_region_id < 200;

# =========

# 6

SELECT sequence FROM dna WHERE seq_region_id = (SELECT seq_region_id FROM seq_region WHERE name = 'contig_39273');


# =========

# 7

SELECT MAX(end_exon_id) FROM translation WHERE translation_id < 200 AND transcript_id IN (SELECT transcript_id FROM transcript WHERE status = 'novel');

# =========

# 8

SELECT gene_id FROM gene WHERE seq_region_id =(SELECT seq_region_id FROM transcript WHERE transcript_id = (SELECT transcript_id FROM translation WHERE end_exon_id = 77777));
SELECT length FROM seq_region WHERE seq_region_id =(SELECT seq_region_id FROM transcript WHERE transcript_id = (SELECT transcript_id FROM translation WHERE end_exon_id = 77777));

# =========