# Fancytalk

Fancytalk is a pointless but sometimes amusing bit of wordplay.  You can think of it as an English-to-English translator: given a sentence, it replaces every word with a synonym, choosing synonyms to optimize for criteria like word length or alliteration.

In its current state, fancytalk doesn't *work* exactly, but does manage to produce things like this:

> The quick brown fox jumped over the lazy dog.
> The straightaway dark-brown slyboots alternated over the faineant frankfurter.

## Criteria

- *Embiggen* prefers longer words.
- *Alliterate* prefers words which *start* with similar phonemes to the previous word.
- *Rhyme* prefers words which *end* with similar phonemes to the previous word.

Phoneme matching is approximated using soundex or metaphone.

## Guts

Fancytalk is really just a small amount of code gluing together a bunch of freely-available natural language processing libraries and corpora:

- [OpenNLP](http://incubator.apache.org/opennlp/) for tokenizing and part-of-speech tagging.
- [WordNet](http://wordnet.princeton.edu) (using the [extjwnl](http://extjwnl.sf.net) library) to find synonyms.
- [Simplenlg](http://code.google.com/p/simplenlg/) to handle verb conjugation and morphology.
- [Commons Codec](http://commons.apache.org/codec/) for phonetic matching.

## Building and Usage

Built using Maven; I've only tried it on Maven 3, but I think it'll work fine with previous versions.  To run, you'll need some large model files for the various NLP libraries:

- WordNet installed in the default location.
- The files "en-sent.bin", "en-token.bin", and "en-pos-maxent.bin" from [OpenNLP](http://opennlp.sourceforge.net/models-1.5/) in a "models" directory under the working directory.

Once it's running, you can just type sentences.  Everytime it sees a newline, it'll translate whatever it's got.

